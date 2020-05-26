function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
function createCommentForm(){
  const commentForm = document.createElement('form');
  commentForm.action = "/data";
  commentForm.method ="POST"

  const label1 = document.createElement('label');
  label1.for ="comment-box";
  label1.innerHTML = "<h6>Leave a comment:</h6>";
  commentForm.appendChild(label1);

  const inputComment = document.createElement('input');
  inputComment.type = "text";
  inputComment.id = "comment-box";
  inputComment.name = "comment";
  commentForm.appendChild(inputComment);

  const commentSubmit = document.createElement('input');
  commentSubmit.type = "submit";
  commentSubmit.id = "comment-submit";
  commentForm.appendChild(commentSubmit);

  const label2 = document.createElement('label');
  label2.for = "numberOfComments";
  label2.innerHTML = "h6>Number of comments to be displayed:</h6>";
  commentForm.appendChild(label2);

  const inputNum = document.createElement('input');
  inputNum.type = "number";
  inputNum.name = "numberOfComments";
  commentForm.appendChild(inputNum);

  const inpNumSubmit = document.createElement('input');
  inpNumSubmit.value = "Fetch";
  commentForm.appendChild(inpNumSubmit);

  const cont = document.getElementById('comment-form-container');
  cont.appendChild(commentForm);

}

function createLoginElement(correspondingURL, logtext){
  const contatiner = document.getElementById("log");
  const loginElem = document.createElement('a');
  loginElem.href = correspondingURL;
  loginElem.innerText = logtext;
  contatiner.appendChild(loginElem);
}

function showComments(){
  const userPromise = fetch('/user')
  .then(response => response.json())
  .then((stateText) =>{
    console.log(JSON.stringify(stateText));
    if(stateText.state){
      console.log('user is logged in');
      createLoginElement(stateText.correspondingURL, "Logout");
      const formELement = document.getElementById("comment-form");
      formELement.style.display = "block";
      const delFormELement = document.getElementById("delete-comment");
      delFormELement.style.display = "block";
      fetchComments();
    }else{
      createLoginElement(stateText.correspondingURL, "Login here");
      const formELement = document.getElementById("comment-form");
      formELement.style.display = "none";
      const delFormELement = document.getElementById("delete-comment");
      delFormELement.style.display = "none";
      console.log('user is not logged in');
    }
  });
}
function fetchComments(){
    fetch('/data')
    .then(response => response.json())
    .then((commentList) => {
      console.log('adding coments to page');
      const commentContainer = document.getElementById("comment-list");
      // commentContainer.innerHTML = JSON.stringify(commentList);
      commentList.forEach((line) => {
        commentContainer.appendChild(createListElement(line.user +": " + line.statement));
      });
    });
}

function deleteComments(){
  console.log('deleting all comments');
  fetch('/delete-data')
  .then(() => fetchComments());
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('comment-submit').disabled = true;
  console.log('disabled at first');
  document.getElementById('comment-box').onkeyup = () => {
      if (document.getElementById('comment-box').value.length > 0){
        console.log('enabling for now');
        document.getElementById('comment-submit').disabled = false;
      }else{
        console.log('disabling again');
        document.getElementById('comment-submit').disabled = true;
      }
  };
});