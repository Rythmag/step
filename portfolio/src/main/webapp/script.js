var imageIndex = 1;
var totalimg = 7;
function changeImage(x) {
  imageIndex += x;
  if(imageIndex > totalimg){
    imageIndex = 1;
  }
  if(imageIndex <= 0){
    imageIndex = totalimg;
  }
  const imgUrl = 'images/img' + imageIndex + '.jpg';

  const imgElement = document.createElement('img');
  imgElement.src = imgUrl;

  const imageContainer = document.getElementById('random-image-container');
  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function fetchComments(){
  // confSubmitButton()
  // .then(
  fetch('/data')
  .then(response => response.json())
  .then((commentList) => {
    console.log('adding coments to page');
    const commentContainer = document.getElementById("comment-list");
    // commentContainer.innerHTML = JSON.stringify(commentList);
    commentList.forEach((line) => {
      commentContainer.appendChild(createListElement(line.statement));
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