import axios from 'axios';
// FOR t IN NetworkTrafficTable COLLECT browser = t.browser WITH COUNT INTO value SORT value DESC RETURN { browser: browser, totalCount: value }
const API_BASE_URL = 'http://localhost:8080/sse';

class BrowserService{

    postData(){
      return axios.post('http://localhost:8081/browserInfo', {
        query: 'SELECT browser@string, eventtimestamp@long FROM networktraffictable LIMIT 4',
        apiKey: localStorage.getItem('apiKey')
      });

    }  
  }

export default new BrowserService();