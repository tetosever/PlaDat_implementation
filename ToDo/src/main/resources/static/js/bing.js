function loadMapScenario() {
    Microsoft.Maps.loadModule('Microsoft.Maps.AutoSuggest', {
        callback: onLoad,
        errorCallback: onError
    });

    function onLoad() {
        var options = {
            maxResults: 5
        };
        var manager = new Microsoft.Maps.AutosuggestManager(options);
        manager.attachAutosuggest('#searchBox', '#searchBoxContainer', selectedSuggestion);
    }

    function onError(message) {
        console.log(message.message)
    }

    function selectedSuggestion(suggestionResult) {
        $('#searchBox').val(suggestionResult.formattedSuggestion);
    }
}