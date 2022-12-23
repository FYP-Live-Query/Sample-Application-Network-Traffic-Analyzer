import axios from 'axios';
// FOR t IN NetworkTrafficTable SORT t.traffic DESC LIMIT 5 RETURN t

class DataService{

    postData(){
      return axios.post('http://localhost:8081/publish', { 
        query: 'SELECT ip@string,browser@string,date@string, traffic@int, eventtimestamp@long, initial_data@string FROM networktraffictable WHERE traffic@int > 9990000',
        apiKey: localStorage.getItem('apiKey')
      });

    }

    postQuery(query) {
      return axios.post('http://localhost:8081/setQuery', { 
        query: query,
        apiKey: localStorage.getItem('apiKey')
      });
    }
  }
  
export default new DataService();