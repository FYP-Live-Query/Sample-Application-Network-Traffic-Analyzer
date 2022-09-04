import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/sse';

class DataService{

    getData(){
        return axios.get(API_BASE_URL);
    }
}

export default new DataService();