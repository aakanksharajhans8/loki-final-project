import pandas as pd
from gcs_loader import load_from_gcs
from transformer import clean_data

def run_etl():
    print("Starting Python ETL pipeline...")
    df = load_from_gcs("my-bucket", "documents/*.csv")
    cleaned = clean_data(df)
    print(cleaned.head())

if __name__ == "__main__":
    run_etl()
