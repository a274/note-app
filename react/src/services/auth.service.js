
import Cookies from 'universal-cookie';
const API_URL = "http://localhost:8083/";


class AuthService {
  cookies = new Cookies();

  login(login, password) {
    return fetch(API_URL + "sign", {
      method: 'GET',
      headers: {
        Authorization: "Basic " + btoa(login + ":" + password)
      },
      credentials: 'same-origin'
    })
      .then(this.handleResponse)
      .then(user => {
        alert(JSON.stringify(user));
        if (user) {
          user.authdata = window.btoa(login + ':' + password);
          localStorage.setItem('user', JSON.stringify(user));
          //if (user.cookie !== null) {
          //   this.cookies.set('Cookie', user.cookie);
          //   alert(this.cookies.get('Cookie')); 
          // }
        }

        return user;
      });
  }

  logout() {
    localStorage.removeItem("user");
    return fetch(API_URL + "logout", {
      method: 'GET',
      headers: {
        Cookie: this.cookies.get("Cookie")
      }
    });
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

  /*
  login(login, password) {
    const result = fetch(API_URL + "sign", {
      method: 'GET',
      headers: {
        Authorization: "Basic " + btoa(login + ":" + password)
      }
    }).then(res =>
      res
    ).catch(error =>
      error
    )
    let data;
    if (!result.ok) {
      data = `UNAUTHENTICATED`;
    }
    data = result.json();
    return data;
  }
*/

  register(login, password) {
    return fetch(API_URL + "sign", {
      method: 'POST',
      headers: {
        Authorization: "Basic " + btoa(login + ":" + password)
      }
    })
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();
