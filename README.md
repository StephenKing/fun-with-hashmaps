# Fun with Hashmap

100k endpoints, each with 4 IMSIs pointing to it in a hashmap

- old: iterating through the map.values() collection to delete entries before insert
- new: maintaining a reverse map endpoint -> IMSIs

## Results

https://plot.ly/~SteffenGebert/1/