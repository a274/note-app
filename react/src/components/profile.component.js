import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import AuthService from "../services/auth.service";
import Note from "../components/note.component";

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      hasNotes: false,
      noteList: {
          id: 0,
          note: "",
          date: ""
      },
      currentUser: { 
        id: 0,
        login: "",
        password: "",
        noteList: [{
            id: 0,
            note: "",
            date: ""
          }
        ] }
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();
    if (!currentUser) this.setState({ redirect: "/" });
    this.setState({ currentUser: currentUser, userReady: true })
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />
    }
    const { hasNotes } = this.state;
    const { currentUser } = this.state;
    let { noteList } = this.state;
    noteList = currentUser.noteList?.map(note => <Note key={note.id} note={note.note} date={note.date} />);

    return (
      <div className="container">
        {(this.state.userReady) ?
          <div className="main_block">
          <div className="row"><h2>Заметки</h2></div>
          <div className="row">
          {noteList}
              <div className="add-card card block1">
                  <form method="POST" action="create">
                      <span className="card-title">Текст заметки:</span>
                      <input type="text" name="note"/>
                      <input className="btn btn-primary" type="submit" value="Добавить" name="Submit"/>
                  </form>
              </div>
          </div>
        </div>
        : null}
      </div>
    );
  }
}
