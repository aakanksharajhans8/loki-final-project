import os
import mysql.connector
from google.cloud import storage
import pandas as pd

# -----------------------
# 1. GCS Setup
# -----------------------
BUCKET_NAME = os.getenv("GCS_BUCKET", "my-doc-bucket")
GCS_PREFIX = os.getenv("GCS_PREFIX", "uploads/")   # Folder inside bucket

storage_client = storage.Client()
bucket = storage_client.bucket(BUCKET_NAME)

# -----------------------
# 2. MySQL Setup
# -----------------------
MYSQL_CONFIG = {
    "host": os.getenv("MYSQL_HOST", "localhost"),
    "port": int(os.getenv("MYSQL_PORT", 3306)),
    "user": os.getenv("MYSQL_USER", "root"),
    "password": os.getenv("MYSQL_PASSWORD", "root"),
    "database": os.getenv("MYSQL_DB", "documentdb"),
}

# -----------------------
# 3. Fetch files from GCS
# -----------------------
def fetch_gcs_files():
    blobs = bucket.list_blobs(prefix=GCS_PREFIX)
    files = []
    for blob in blobs:
        if not blob.name.endswith("/"):  # skip folders
            files.append({
                "document_name": blob.name.split("/")[-1],
                "file_size": blob.size,
                "file_type": blob.content_type or "unknown",
            })
    return pd.DataFrame(files)

# -----------------------
# 4. Load into MySQL
# -----------------------
def load_into_mysql(df: pd.DataFrame):
    conn = mysql.connector.connect(**MYSQL_CONFIG)
    cursor = conn.cursor()

    for _, row in df.iterrows():
        # Insert metadata
        cursor.execute("""
            INSERT INTO document_metadata (file_name, upload_time)
            VALUES (%s, NOW())
        """, (row["document_name"],))

        doc_id = cursor.lastrowid

        # Insert into fact_documents
        cursor.execute("""
            INSERT INTO fact_documents (document_id, file_size, file_type, user_id)
            VALUES (%s, %s, %s, %s)
        """, (doc_id, row["file_size"], row["file_type"], 1))  # static user_id=1 for now

    conn.commit()
    cursor.close()
    conn.close()

# -----------------------
# 5. Run ETL
# -----------------------
if __name__ == "__main__":
    print("Fetching files from GCS...")
    df = fetch_gcs_files()
    if not df.empty:
        print(f"Loaded {len(df)} files from GCS")
        load_into_mysql(df)
        print("ETL completed successfully ✅")
    else:
        print("No files found in GCS ❌")
