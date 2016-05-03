warnCol='\033[0;33m'	# Color (light yellow) to print warning message on shell 
noCol='\033[0m'			# No Color


usr=madD7
proj=JavaChatRoom


init: 
	git init
	git remote add origin https://github.com/$(usr)/$(proj).git
	@printf "${warnCol}Remote $(proj) initialized${noCol}\n"
	
commit:
	git add $(file)
	git commit -m "$(msg)"
	@printf "${warnCol}Commiting $(file) to the remote server${noCol}\n"
	@printf "${warnCol}This may take some time.....${noCol}\n"
	git push -u origin master

pull:
	@printf "${warnCol}Fetching from remote server${noCol}\n"
	@printf "${warnCol}This may take some time.....${noCol}\n"
	git fetch
	git checkout $(file)
	
