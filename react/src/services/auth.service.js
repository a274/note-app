const API_URL = "http://localhost:8083/";

class AuthService {
  login(login, password) {
    return fetch (API_URL + "sign", {
        method: 'GET',
        headers: {
            Authorization: "Basic " + btoa(login + ":" + password)
        }
    })
      .then(response => response.json())
      .then(json => {
        console.log('parsed json', json);
        if (json) { 
          alert(JSON.stringify(json));
          localStorage.setItem("user", JSON.stringify(json));
        }
        return json;
      });

  }

  register(login, password) {
    return fetch (API_URL + "sign", {
      method: 'POST',
      headers: {
          Authorization: "Basic " + btoa(login + ":" + password)
      }
  })
  }

  logout() {
    localStorage.removeItem("user");
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();
