from pymongo import MongoClient

# 로컬용
# mongodb_URI = "mongodb://localhost:27017/"
# client = MongoClient(mongodb_URI)

# 서버용
# username = "eagle"
# password = "k5b201eagle"
# ip_address = "13.124.101.173"
# port = "27017"
# mongodb_URI = f"mongodb://{username}:{password}@{ip_address}:{port}"
# client = MongoClient(mongodb_URI)

# 더 넓은 서버용
ip_address = "13.124.101.173"
replicaSet = "docker-rs"
mongodb_URI = f"mongodb://{ip_address}:9042,{ip_address}:9142,{ip_address}:9242/?replicaSet={replicaSet}"
client = MongoClient(mongodb_URI)

db = client['Urls']
