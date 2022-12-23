import axios from 'axios';


class DataService{

    postData(){
      return axios.post('http://localhost:8081/publish', { 
        // query: 'SELECT ip@string,browser@string,date@string, traffic@int, eventtimestamp FROM networktraffictable WHERE traffic@int > 9990000',
        query: 'FOR t IN networktraffictable SORT t.traffic DESC LIMIT 5 RETURN t',
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