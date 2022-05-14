const API_URL = "http://localhost:8083/";


class NoteService {

  deleteNote(noteId, curUser) {
    const DEL_URL = API_URL + "delete?id=" + noteId;
    return fetch(DEL_URL, {
      method: 'GET',
      headers: {
        Authorization: "Basic " + curUser.authdata
      }
    })
      .then(this.handleResponse)
      .then(user => {
        alert(JSON.stringify(user));
        if (user) {
          user.authdata = curUser.authdata;
          localStorage.setItem('user', JSON.stringify(user));
        }
        return user;
      });
  }

  handleResponse(response) {
    return response.text().then(text => {
      alert("error");
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