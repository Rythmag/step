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
