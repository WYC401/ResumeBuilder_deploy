
function addtoHtml(jobTitle, keywords, company) {

    //jobTitledPara = <p><strong>Job Title:</strong> Software Engineer</p>
    const jobTitledPara =document.createElement("p");
    jobTitledPara.setAttribute("id", "jobTitle");
    //Job Title:
    const jobTitlePrompt = document.createElement("strong");
    jobTitlePrompt.innerHTML = "Job Title: ";
    //content of job title like Software Engineer
    const jobTitleText = document.createTextNode(jobTitle);
    jobTitledPara.appendChild(jobTitlePrompt);
    jobTitledPara.appendChild(jobTitleText);

    const companyPara = document.createElement("p");
    const companyPrompt = document.createElement("strong");
    companyPrompt.innerText = "Company:";
    const companyName = document.createTextNode(company);
    companyPara.appendChild(companyPrompt);
    companyPara.appendChild(companyName);



    const keywordPara = document.createElement("p");
    const keywordPrompt = document.createElement("strong");
    keywordPrompt.innerText = "Keywords: ";
    const keywordsContent = document.createTextNode(keywords.join(", "));
    keywordPara.appendChild(keywordPrompt);
    keywordPara.appendChild(keywordsContent);

    const submitButton = document.createElement("button");
    submitButton.innerText = "Download Resume";
    submitButton.addEventListener('click', async ()=> {
        
        // await fetch(`http://127.0.0.1:8080/resume?jobKeywords=java,sql`, {
        //     method: 'GET',
        // }).then((response) => {
        //     console.log(response);
        // }).catch((err)=>{
        //     console.log(err);
        // });
        const htmlString = '<html><body><h1>Hello, world!</h1></body></html>';

        const newTab = window.open();
        newTab.document.open();
        newTab.document.write(htmlString);
        newTab.document.close();
    });
    document.body.appendChild(jobTitledPara);
    document.body.appendChild(companyPara);
    document.body.appendChild(keywordPara);
    document.body.appendChild(submitButton);
}

chrome.storage.local.get("data", function (result) {
    console.log(result);
    addtoHtml(result.data.jobTitle, result.data.keywords, result.data.company);
  });

  chrome.storage.local.remove("data", function() {
    console.log("Data removed successfully.");
  });
  

// Call the getReceivedData() function from the background script to access the shared variable



