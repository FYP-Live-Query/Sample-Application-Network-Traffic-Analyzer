import axios from 'axios';
// FOR t IN NetworkTrafficTable COLLECT browser = t.browser WITH COUNT INTO value SORT value DESC RETURN { browser: browser, totalCount: value }

class BrowserService{

    postData(){
      return axios.post('http://localhost:8081/browserInfo', {
        query: 'SELECT browser@string, eventTimestamp@long FROM networkTraffic LIMIT 4',
        apiKey: localStorage.getItem('apiKey'),
        id: "QWERTY"
      });

    }  
  }

export default new BrowserService();