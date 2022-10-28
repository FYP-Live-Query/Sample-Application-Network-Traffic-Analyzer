import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/sse';

class BrowserService{

    postData(){
      return axios.post('http://localhost:8081/browserInfo', { 
        query: 'FOR t IN network_traffic COLLECT browser = t.browser WITH COUNT INTO value SORT value DESC RETURN { browser: browser, totalCount: value }',
        apiKey: localStorage.getItem('apiKey')
      });

    }  
  }

export default new BrowserService();