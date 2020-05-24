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
  console.log('adding to page');
  const msgContainer = document.getElementById('msgcontainer');
  msgContainer.innerHTML = msgText;
}