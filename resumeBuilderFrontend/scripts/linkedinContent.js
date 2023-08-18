var prekeywords = [
    'Java',
    'Spring Boot',
    'AWS',
    'SQL',
    'python',
    'Flask',
    'JavaScrip',
    'Node'
];

function scapper(prekeywords) {
    var jobTitle = document.getElementsByClassName("t-24 t-bold jobs-unified-top-card__job-title")[0].innerText;
    var company = document.getElementsByClassName("ember-view t-black t-normal")[0].innerText;
    function textNodesUnder(node){
        var all = [];
        for (node=node.firstChild;node;node=node.nextSibling){
        if (node.nodeType==3) all.push(node);
        else all = all.concat(textNodesUnder(node));
        }
        return all;
    }
    let textNodes = textNodesUnder(document.getRootNode());
    var allText = "";
    textNodes.forEach((x) => {
        allText+=x.textContent.trim();
    });

    function filterStrings(prekeywords, webText) {
        var filteredStrings = prekeywords.filter(function(string) {
            return webText.toLowerCase().includes(string.toLowerCase());
        });

        return filteredStrings;
    }

    var keywordsFiltered = filterStrings(prekeywords, allText);
    const data = {
        jobTitle: jobTitle,
        company: company,
        keywords: keywordsFiltered
    };
    return data;
}



  
  function updateJobpageInfo() {
    const data = scapper(prekeywords);
    chrome.storage.local.set({ data: data });
  }
  
  // Observe DOM changes using MutationObserver
  const observer = new MutationObserver(function (mutations) {
    updateJobpageInfo();
  });
  
  // Start observing the target node and its descendants
  const targetNode = document.body;
  const config = { childList: true, subtree: true };
  observer.observe(targetNode, config);
  
  // Update word count when the content script is injected
  updateJobpageInfo();
