const API_URL = "http://localhost:8083/";


class AuthService {
  login(login, password) {
    return fetch(API_URL + "sign", {
      method: 'GET',
      headers: {
        Authorization: "Basic " + btoa(login + ":" + password)
      }
    })
      .then(this.handleResponse)
      .then(user => {
        if (user) {
          user.authdata = window.btoa(login + ':' + password);
          localStorage.setItem('user', JSON.stringify(user));
        }
        return user;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  handleResponse(response) {
    return response.text().then(text => {
      const data = text && JSON.parse(text);

      if (!response.ok) {
        if (response.status === 401) {
          alert("Incorrect login or password");
        }

        const error = (data && data.message) || response.statusText;
        return Promise.reject(error);
      }

      return data;
    });
  }

  /*
  login(login, password) {
    return fetch (API_URL + "sign", {
        method: 'GET',
        headers: {
            Authorization: "Basic " + btoa(login + ":" + password)
        }
    })
      .then(response => response.json())
      .then(json => {
        alert(JSON.stringify(json));
        console.log('parsed json', json);
        if (json) { 
          alert(JSON.stringify(json));
          localStorage.setItem("user", JSON.stringify(json));
        }
        return json;
      });
  }*/

  register(login, password) {
    const postData = {
      "login": login,
      "password": password,
    };

    return fetch(API_URL + "sign", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: "Basic " + btoa(login + ":" + password)
      },
      body: JSON.stringify(postData)
    })
      .then(this.handleResponse)
      .then(user => {
        if (user) {
          user.authdata = window.btoa(login + ':' + password);
          localStorage.setItem('user', JSON.stringify(user));
        }
        return user;
      });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();
