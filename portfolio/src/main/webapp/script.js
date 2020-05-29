function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
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
  .then(() => {
    location.reload(true);
    fetchComments()
  });  
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