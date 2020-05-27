function fetchImages(){
    fetch('/my-form-handler')
    .then(response => response.json())
    .then(imagelist => {
        console.log(JSON.stringify(imagelist)); 
        imagelist.forEach((line) => {
            const imgContainer = document.getElementById("image-container");
            console.log( JSON.stringify(line));
            // 
            imgContainer.appendChild(appendImageElement(line.imageUrl));
            const imgMSG = document.createElement('p');
            imgMSG.innerText = "message: " + line.message;
            imgContainer.appendChild(imgMSG);
        });
    });
}
function appendImageElement(imageSrc){
    const imgElem = document.createElement('img');
    imgElem.src = imageSrc;
    return imgElem;
}