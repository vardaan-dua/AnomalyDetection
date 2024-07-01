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

def load_data(file_path):
    # Read the Excel file
    df = pd.read_excel(file_path, usecols=[0, 1, 2, 3])

    # Ensure the column names match your Excel file
    df.columns = ['timestamp', 'source', 'api_call_time', 'cpu_time']

    # Parse timestamps to datetime
    df['timestamp'] = pd.to_datetime(df['timestamp'])

    # Convert timestamps to integers
    df['timestamp'] = df['timestamp'].view(np.int64)

    # Normalize timestamps using standard scaling
    scaler = StandardScaler()
    df['timestamp'] = scaler.fit_transform(df['timestamp'].values.reshape(-1, 1))

    # Normalize api_call_time and cpu_time to range [-1, 1]
    min_max_scaler = MinMaxScaler(feature_range=(-1, 1))
    df[['api_call_time', 'cpu_time']] = min_max_scaler.fit_transform(df[['api_call_time', 'cpu_time']])

    # # Encode sources as categorical
    # source_encoder = LabelEncoder()
    # df['source'] = source_encoder.fit_transform(df['source'])

    return df


def main():
    file_path = "/Users/vardaan.dua/Downloads/All-Messages-Search-Result (2).xlsx"

    try:
        # Load data
        data = load_data(file_path)

        # Extract features for clustering
        features = data[['timestamp', 'source', 'api_call_time', 'cpu_time']].values

        # Determine the optimal number of clusters
        num_clusters = 10

        # Perform KMeans clustering with custom distance metric
        kmeans = KMeans(n_clusters=num_clusters, random_state=0)
        kmeans.fit(features)


        # Get cluster centroids
        centroids = kmeans.cluster_centers_

        # Assign clusters based on the custom distance matrix
        cluster_labels = np.argmin(distance_matrix, axis=1)

        # Get cluster centroids
        centroids = kmeans.cluster_centers_

        # Add cluster labels to the data
        data['cluster'] = cluster_labels

        # Save the data with cluster labels to a new CSV file
        data.to_excel("/Users/vardaan.dua/Desktop/AnomalyDetection/src/main/clusters.xlsx", index=False)


        # Print centroids
        print("Centroids:")
        print(centroids)

    except Exception as e:
        print(f"Failed to load and process data: {str(e)}")

if __name__ == "__main__":
    main()
