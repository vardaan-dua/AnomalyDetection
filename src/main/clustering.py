import pandas as pd
import numpy as np
from sklearn.cluster import KMeans
import nltk
import re
from sklearn.preprocessing import normalize
from sklearn.feature_extraction.text import TfidfVectorizer
from collections import Counter
from nltk.corpus import words
import argparse
# import pymongo

# nltk.download('words')
eng_words = set(words.words())

def clean_text(text):
    # Remove punctuation and special characters
    text = re.sub(r'[^\w\s]|[\d]', ' ', text)
    # Convert text to lowercase
    text = text.lower()
    text = text.strip()
    words = text.split()
    # Remove non-dictionary words
    words = [word for word in words if word in eng_words or len(word)>3]
    return ' '.join(words)


def load_data(file_path , min_time , max_time):
    # Read the Excel file
    df = pd.read_excel(file_path, usecols=[0,2],nrows=100000)
    # Ensure the column names match your Excel file
    df.columns = ['timestamp','message']
    
    df['timestamp_col'] = df['timestamp'].apply(lambda x: re.search(r'\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}Z', x).group() if re.search(r'\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}Z', x) else None)    
    
    df['timestamp_col'] = pd.to_datetime(df['timestamp_col'], format='%Y-%m-%dT%H:%M:%S.%fZ')
    
    min_time = pd.to_datetime(min_time,format='%Y-%m-%dT%H:%M:%S.%fZ')
    
    max_time = pd.to_datetime(max_time,format='%Y-%m-%dT%H:%M:%S.%fZ')
        
    df = df[(df['timestamp_col']>=min_time) & (df['timestamp_col']<=max_time)]    
    
    df['cleaned_message'] = df['message'].apply(clean_text) 
    
    return df

def main(begin_time, end_time):
    file_path = "/Users/vardaan.dua/Downloads/All-Messages-Search-Result (6).xlsx"
    try:
        # Load data
        beginTimeStamp = begin_time
        endTimeStamp = end_time
        data = load_data(file_path,beginTimeStamp,endTimeStamp)

        vectorizer = TfidfVectorizer(stop_words='english')
    
        tfidf_matrix = vectorizer.fit_transform(data['cleaned_message'])
    
        tfidf_matrix_normalized = normalize(tfidf_matrix)

        num_clusters = min(int(data.size/20),200)

        kmeans = KMeans(n_clusters=num_clusters, random_state=42)

        kmeans.fit(tfidf_matrix_normalized)

        data['Cluster'] = kmeans.labels_

        data.to_excel('clusters.xlsx', index=False)

        representative_messages = []
        for cluster_num in range(num_clusters):
            cluster_data = data[data['Cluster'] == cluster_num]
            if not cluster_data.empty:
                most_common_message = Counter(cluster_data['cleaned_message']).most_common(1)[0]
                representative_messages.append({
                'Cluster': cluster_num,
                'Representative Message': most_common_message[0],
                'Count': most_common_message[1]
                })


        # connection_string = "mongodb+srv://admin:admin@cluster0.os2lm19.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
       
        # client = pymongo.MongoClient(connection_string)
      
        # db = client.representative_messages
      
        # collection = db.representative_messages
      
        representative_df = pd.DataFrame(representative_messages)
      
        representative_df = representative_df.sort_values(by='Count', ascending=False)
        
        for index, row in representative_df.iterrows():
             message_dict = {"Cluster": row['Cluster'],"Representative Message": row['Representative Message'],"Count": row['Count']}
            #  collection.insert_one(message_dict)
        
        representative_df.to_excel('representative_messages.xlsx',index=False)

    except Exception as e:
        print(f"Failed to load and process data: {str(e)}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("begin_time") #help="Begin timestamp in the format yyyy-MM-ddTHH:mm:ss.SSSZ")
    parser.add_argument("end_time") #help="End timestamp in the format yyyy-MM-ddTHH:mm:ss.SSSZ")
    args = parser.parse_args()
    # print(args.begin_time)
    # print(args.end_time)
    main(args.begin_time, args.end_time)

