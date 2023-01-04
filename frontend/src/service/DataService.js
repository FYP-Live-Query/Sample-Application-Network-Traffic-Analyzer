import axios from 'axios';
// FOR t IN NetworkTrafficTable SORT t.traffic DESC LIMIT 5 RETURN t

class DataService{

    postData(){
      return axios.post('http://localhost:8081/publish', { 
        query: 'SELECT ip@string,browser@string,date@string, traffic@int, eventtimestamp@long, initial_data@string FROM networktraffictable ORDER BY traffic@int DESC LIMIT 5',
        apiKey: localStorage.getItem('apiKey'),
        id: "QWERTY"
      });

    }

    postQuery(query) {
      return axios.post('http://localhost:8081/setQuery', { 
        query: query,
        apiKey: localStorage.getItem('apiKey'),
        id: "QWERTY"
      });
    }
  }
  
export default new DataService();