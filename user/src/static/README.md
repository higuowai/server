# An angular seed project

This is a seed project for bootstapping new angular single-page web app projects. It uses the angular js (and the ui-route plugin) and twitter bootstrap CSS.

The project also enables the token-based authentication mechanism - when accessing a protected page, it first detects the token from cookie and will redirect to login page if the token is missing. It is configured in the "config.js" file.

If you want to add more pages, you can add it to "views" folder, and add it's corresponding business logic to controllers (i.e. the "controller.js" file). Additionally, you may extract the common, re-useable business logics into service functions and put them into the "service.js" file.

If you'll use other plugins, add them to the "plugins" folder.
