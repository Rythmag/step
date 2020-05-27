function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
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

function fetchMessage(){
  console.log('fetching msg from server');
  const msgFromServer = fetch('/data');
  msgFromServer.then(convertToText);
}
function convertToText(msgFromServer){
  console.log('convert to text');
  const msgText = msgFromServer.text();
  msgText.then(addMsgToPage);
}
function addMsgToPage(msgText){
  console.log('adding to page:' + msgText);
  const msgContainer = document.getElementById('msgcontainer');
  msgContainer.innerHTML = msgText;
}
function fetchMsgUsingOneFunction(){
  console.log('arr1');
  fetch('/data')
  .then(function(response){
    console.log('text extracted');
    return response.text()
  })
  .then(function(msgText){
    console.log('adding to page:' + msgText);
    const msgContainer = document.getElementById('msgcontainer');
    msgContainer.innerHTML = msgText;
  })
}
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
function fetchJson(){
  console.log('fetching from server')
  const response = fetch('/data')
  .then(function(response){
    console.log('parsing json');
    return response.json();
  }).then(function(listItems){
    console.log('adding to page');
    const datacontainer = document.getElementById('msgcontainer');
    // datacontainer.innerHTML = listItems;
    // datacontainer.innerHTML = JSON.stringify( listItems);
    const ulElement = document.createElement('ul');
    ulElement.appendChild(createListElement( listItems.comments[0]) );
    ulElement.appendChild(createListElement( listItems.comments[1]) );
    ulElement.appendChild(createListElement( listItems.comments[2]) );
    datacontainer.appendChild(ulElement);
  }) 
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
  .then(response => response.json())
  .then(() => {
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