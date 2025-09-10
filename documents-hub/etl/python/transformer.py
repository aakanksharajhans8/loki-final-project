import pandas as pd

def clean_data(df: pd.DataFrame):
    df = df.drop_duplicates()
    df['uploaded_at'] = pd.to_datetime(df['uploaded_at'])
    return df
