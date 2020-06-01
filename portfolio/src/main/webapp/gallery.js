const imageIndex = 1;
const totalimg = 7;
function changeImage(x) {
  imageIndex = (imageIndex + x + totalimg) % totalimg;
  const imgUrl = 'images/img' + (imageIndex + 1) + '.jpg';
  const imgElement = document.createElement('img');
  imgElement.src = imgUrl;

  const imageContainer = document.getElementById('random-image-container');
  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
}
