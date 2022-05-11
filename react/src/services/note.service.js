const API_URL = "http://localhost:8083";

class NoteService {
    deleteNote(noteId, login, password) {
        const del_url = API_URL + "?id=" + noteId;
        return fetch(del_url, {
            method: 'DELETE',
            headers: {
                Authorization: "Basic " + btoa(login + ":" + password)
            },
            credentials: 'same-origin',
            withCredentials: true

        })
        .then(response => {
            alert(JSON.stringify(response));
            response.json()})
        .then(json => {
          if (json) { 
            json.password = this.cookies.get('Cookie'); 
            localStorage.setItem("user", JSON.stringify(json));
          }
          return json;
        });


    }


}

export default new NoteService();
