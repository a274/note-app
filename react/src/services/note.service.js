import Cookies from 'universal-cookie';
const API_URL = "http://localhost:8083/";

class NoteService {
    cookies = new Cookies();

    deleteNote(noteId, login, password) {
        const del_url = API_URL + "delete?id=" + noteId;
        this.cookies.set('Cookie', password);
        return fetch(del_url, {
            method: 'GET',
            headers: {
                Authorization: "Basic " + btoa(login + ":" + password)
            }
        })
            .then(this.handleResponse)
            .then(user => {
                if (user) {
                    //user.authdata = window.btoa(login + ':' + password);
                    if (user !== null) {
                        user.password = this.cookies.get('Cookie');
                    }
                    localStorage.setItem('user', JSON.stringify(user));
                }

                return user;
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


}

export default new NoteService();
