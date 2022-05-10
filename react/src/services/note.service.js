import axios from 'axios';
import AuthService from '../services/auth.service';
const API_URL = "http://localhost:8083";

class NoteService {
    deleteNote(noteId, login, password) {
        //AuthService.login(login, password);
        const url = API_URL;
        return fetch(url, {
            method: 'GET',
            headers: {
                Authorization: "Basic " + btoa(login + ":" + password)
            }

        }).then(this.handleResponse)
            .then(user => {
                alert(JSON.stringify(user));
                // login successful if there's a user in the response
                if (user) {
                    alert(JSON.stringify(user));
                    // store user details and basic auth credentials in local storage 
                    // to keep user logged in between page refreshes
                  //  user.authdata = window.btoa(login + ':' + password);
                //    localStorage.setItem('user', JSON.stringify(user));
                }

                return user;
            });


    }

    handleResponse(response) {
        return response.text().then(text => {
            alert("s");
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
    deleteNote(noteId) {
      return axios
        .delete(API_URL + '?id=' + noteId)
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
    */
}

export default new NoteService();
