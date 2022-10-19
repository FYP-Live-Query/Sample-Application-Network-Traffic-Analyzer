import axios from 'axios';


class DataService{

    postData(){
      return axios.post('http://localhost:8081/publish', { 
        query: 'FOR t IN network_traffic SORT t.traffic DESC LIMIT 5 RETURN t',
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