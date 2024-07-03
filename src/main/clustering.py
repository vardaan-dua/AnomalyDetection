# import pandas as pd
# import numpy as np
# from sklearn.cluster import KMeans
# from sklearn.preprocessing import StandardScaler, LabelEncoder
#
# def load_data(file_path):
#     # Read the Excel file
#     df = pd.read_excel(file_path, usecols=[0, 1, 2, 3])
#
#     # Ensure the column names match your Excel file
#     df.columns = ['timestamp', 'source', 'api_call_time', 'cpu_time']
#
#     # Parse timestamps to datetime
#     df['timestamp'] = pd.to_datetime(df['timestamp'])
#
#     # Convert timestamps to integers
#     df['timestamp'] = df['timestamp'].view(np.int64)
#
#     # Normalize timestamps using standard scaling
#     scaler = StandardScaler()
#     df['timestamp'] = scaler.fit_transform(df['timestamp'].values.reshape(-1, 1))
#
#     # Encode sources as categorical
#     source_encoder = LabelEncoder()
#     df['source'] = source_encoder.fit_transform(df['source'])
#
#     return df
#
# def main():
#     file_path = "/Users/vardaan.dua/Downloads/All-Messages-Search-Result (2).xlsx"
#
#     try:
#         # Load data
#         data = load_data(file_path)
#
#         # Extract features for clustering
#         features = data[['timestamp', 'source', 'api_call_time', 'cpu_time']].values
#
#         # Perform KMeans clustering
#
#         num_clusters = 3  # Adjust as needed
#         kmeans = KMeans(n_clusters=num_clusters, random_state=0)
#         kmeans.fit(features)
#
#         # Get cluster centroids
#         centroids = kmeans.cluster_centers_
#
#         # Print centroids
#         print("Centroids:")
#         print(centroids)
#
#     except Exception as e:
#         print(f"Failed to load and process data: {str(e)}")
#
# if __name__ == "__main__":
#     main()

import pandas as pd
import numpy as np
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler, MinMaxScaler, LabelEncoder
from sklearn.metrics import silhouette_score
from nltk.corpus import stopwords
import nltk
import re
from sklearn.preprocessing import normalize
from sklearn.feature_extraction.text import TfidfVectorizer

def clean_text(text):
    # Remove punctuation and special characters
    text = re.sub(r'[^\w\s]|[\d]', ' ', text)
    # Convert text to lowercase
    text = text.lower()
    text = text.strip()
    return text

def load_data(file_path):
    # Read the Excel file
    df = pd.read_excel(file_path, usecols=[2] , nrows=100)

    # Ensure the column names match your Excel file
    df.columns = ['message']
    
    df['cleaned_message'] = df['message'].apply(clean_text) 
        
    return df

def print_cluster_elements(df, cluster_number):
    cluster_data = df[df['Cluster'] == cluster_number]
    
    if cluster_data.empty:
        print(f"No elements found in Cluster {cluster_number}.")
    else:
        print(f"Elements in Cluster {cluster_number}:")
        for idx, row in cluster_data.iterrows():
            print(f"- {row['message']}")
# /Users/vardaan.dua/desktop/AnomalyDetection/myenv/bin/python3
def main():
    file_path = "/Users/vardaan.dua/Downloads/All-Messages-Search-Result(3).xlsx"

    try:
        # Load data
        print("loader mein ghus gya")
        data = load_data(file_path)

        vectorizer = TfidfVectorizer(stop_words='english')
    
        # Fit and transform the cleaned messages
        tfidf_matrix = vectorizer.fit_transform(data['cleaned_message'])
    
        tfidf_matrix_normalized = normalize(tfidf_matrix)
        num_clusters = min(int(data.size/20),200)
        kmeans = KMeans(n_clusters=num_clusters, random_state=42)

        kmeans.fit(tfidf_matrix_normalized)

        # Adding cluster labels to the DataFrame
        data['Cluster'] = kmeans.labels_

        # Output
        data.to_excel('clusters.xlsx', index=False)
        # print(data[['message', 'cleaned_message', 'Cluster']])
        # # Extract features for clustering
        # features = data[['message']].values

        # # Determine the optimal number of clusters
        # num_clusters = 10

        # # Perform KMeans clustering with custom distance metric
        # kmeans = KMeans(n_clusters=num_clusters, random_state=0)
        # kmeans.fit(features)


        # # Get cluster centroids
        # centroids = kmeans.cluster_centers_

        # # Assign clusters based on the custom distance matrix
        # cluster_labels = np.argmin(distance_matrix, axis=1)

        # # Get cluster centroids
        # centroids = kmeans.cluster_centers_

        # # Add cluster labels to the data
        # data['cluster'] = cluster_labels

        # # Save the data with cluster labels to a new CSV file
        # data.to_excel("/Users/vardaan.dua/Desktop/AnomalyDetection/src/main/clusters.xlsx", index=False)


        # # Print centroids
        # print("Centroids:")
        # print(centroids)

    except Exception as e:
        print(f"Failed to load and process data: {str(e)}")

if __name__ == "__main__":
    main()
