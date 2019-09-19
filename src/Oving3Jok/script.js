const ul = document.getElementById('article');
const url = "http://localhost:8080/frontpage";

function createNode(element) {
    return document.createElement(element); // Create the type of element you pass in the parameters
}

function append(parent, el) {
    return parent.appendChild(el); // Append the second parameter(element) to the first one
}

/*fetch(url)
    .then(res => res.json())
    .then(function (data) {
        let articles = data.results; // Get the results
        return articles.map(function (articles) { // Map through the results and for each run the code below
            let li = createNode('li'), //  Create the elements we need
                img = createNode('img'),
                span = createNode('span');
            img.src = articles.image;  // Add the source of the image to be the src of the img element
            span.innerHTML = `${articles.headline}`; // Make the HTML of our span to be the first and last name of our author
            append(li, img); // Append all our elements
            append(li, span);
            append(ul, li);
        })
    })
    .catch(function (error) {
        console.log(error);
    });*/


fetch(url)
    .then(res => res.json())
    .then(function (data) {})
    .catch(function (error) {
        console.log(error);
    });