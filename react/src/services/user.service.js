import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8083/';

class UserService {
  getPublicContent() {
    return axios.get(API_URL);
  }

  getUserBoard() {
    return axios.get(API_URL, { headers: authHeader() });
  }
}

export default new UserService();
