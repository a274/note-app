import React from "react";
import CheckButton from "react-validation/build/button";

function Note(props) {
    return(
        <div className="col-lg-4 col-sm-6 books">
        <div className="block1 card">
            <div className="card-body">
                <p className="card-text">{props.note}</p>
                <p className="align-start text-right font-weight-lighter card-text">{props.date}</p>
                <a href="" className="btn btn-primary">Удалить</a>
            </div>
        </div>
    </div>
    )}
   
   export default Note;