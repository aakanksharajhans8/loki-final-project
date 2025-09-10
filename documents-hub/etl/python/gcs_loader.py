from google.cloud import storage
import pandas as pd
import io

def load_from_gcs(bucket_name, prefix):
    client = storage.Client()
    bucket = client.bucket(bucket_name)
    blobs = bucket.list_blobs(prefix=prefix)
    frames = []
    for blob in blobs:
        if blob.name.endswith(".csv"):
            data = blob.download_as_bytes()
            df = pd.read_csv(io.BytesIO(data))
            frames.append(df)
    return pd.concat(frames, ignore_index=True)
