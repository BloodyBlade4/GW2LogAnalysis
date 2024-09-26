# GW2LogAnalysis
## Contact 
If you'd like to leave feedback, suggestions, or report a bug, reach out on the Discord server: [Link here.](https://discord.gg/3qMMDJ2rm2)

## Summary:
Application to create tables with any desired data from ArcDPS logs, with complex filtering. The application will organize your desired stats into both csv, a default spreadsheet model, or into a .xlsx spreadsheet with tables, average formulas, and sorting already created for Excel. Integration of your copy of Elite Insights allows parsing directly from ArcDPS’s original .zevtc files and embedded html files into the tables.  


## OUTPUT:
Two spreadsheet formats will be created, .csv and .xlsx, with content based on your user settings. The .xlsx file is an Excel spreadsheet document that is already formatted with sortable tables and averaging formulas, .csv has been left as an output in case users are missing Excel, but is merely a comma separated document. 

### Features
- Integrates Elite Insights to parse directly from .zevtc files. _Must have Elite Insights downloaded locally._
- The table can be sorted by any column, reordering each row accordingly. 
- Average formulas included at the bottom of each column. Click on the excel formula to easily change to another formula function, such as "min", "max", etc.
- HTML links can be embedded into the excel file to show the full report. 

### Filtering
Many filtering features have already been added, more will likely come as different needs arise. Some filtering examples include: Percentage of Time Alive, Distance to Commander, Duration, Weapon filters, Location/Game Mode/Encounters, Specialization, and more. 

### Menu GUI
The menu for the program, as of v0.9:

![Menu image](./src/main/resources/LogAnalysisMenu.png)

### Example
The Excel document output will look similar to the following image: 
![Example Excel document](./src/main/resources/LogAnalysisExcelexample.png)
  

## Use/Instructions
Go to the Release section and download the latest jar file (An executable Java file).

### general Use
- By hovering over text/fields, tool tips will show up giving additional details on the fields functionality. 
- There are few fields that are required to run the program, if they are not filled out, a window will pop up informing you of the missing field. 
- If a log fails to pass filtering options, the filter that the log failed to pass will be displayed in the parsing window. 

### Additional Info
- Elite Insight (EI) linking: By checking the "Parse from .zevtc" option, on the home screen, additional options will show up to set up the parsing. You must have the Elite Insights Parser downloaded locally, you can do so [HERE](https://github.com/baaron4/GW2-Elite-Insights-Parser). Once installed and unzipped, navigate to and select the EI application file (.exe) through the GW2 Log Analysis application, using the "Elite Insights Directory" button. 
- HTML Embedding: Embedding html will create a click-able link, opening a page in your default browser that looks similar to a log show on dps.repot. Embedding html can greatly increase the size of your excel file and the time it takes to parse logs.
- Filter dates by log: This is a filtering option available in the "Advanced Filtering" tab. By default this is disabled and the parser will filter logs based on the time stamp on the file. By enabling this, the parser will filter log dates based on the information inside the log, meaning every single log will be parsed -even if it is to be rejected. Enabling this option can greatly increase parsing time, depending on use case. 


## Resources
### Feedback, suggestions, bug reports
If you'd like to leave feedback, suggestions, or report a bug, join the Discord server and let us know. [Link here.](https://discord.gg/3qMMDJ2rm2)

#### Upcoming improvements:
This project is still in development stage. The main goal was to pull all the necessary information out of log files to generate comparisons between builds. With this in mind, there are many features that should still be implemented. However, looking at the community of Guild Wars, there are many other use cases that this project could expand to. The goals currently set are: 
- Finish listing all the buff skills available. Currently, some of the most prominent skill effects are listed, but there are many more and some that should be read as percentages aren't yet.  
- Implementing an "update" button, connected to the Github release page. 
- Incoming/outgoing damage. This is a tricky one to implement, while maintaining legibility. Current ideas are 
    - Top damage skills and their percent/total damage.
    - Allowing selection of skills to display percent and total.
    - Embedded table with the same format as dps.report.
- Ability to generate charts into output. 
- Fix html embedded object so they do not resize upon use.

##### Questionable Ideas
- Integrate dps.report url links, instead of embedded html. The difficulties are that the program currently doesn't require any connection and this will create a LOT of calls to dps.report, which may not be very polite to their servers.
- File management (is this a desired feature?)
	- Ability to zip used log/generated files next to output
- Ability to generate multiple tables/sheets in excel, either adding onto previous sheets or having multiple "runs" (for lack of a better term). With the current idea of comparison, one would need to copy and paste the output from multiple program runs.
	

### Resources Used/referenced:
Many thanks to the people that created the resources that make this project work! Guild Wars is an ever growing community that so many people have poured love into. It is a beautiful thing. (_While this project is drawn from and utilizes many of the resources listed here, the listed resources/creators do not have any association with this project and are not responsible for its functionality nor use._)
- [Elite Insights](https://github.com/baaron4/GW2-Elite-Insights-Parser/tree/master)
	- [Elite Insight JSON documentation](https://baaron4.github.io/GW2-Elite-Insights-Parser/Json/index.html)
- Eclipse IDE/Java
- Jackson Library (For xml files)
- Simple JSON (JSON file reading)
- Apache POI (For excel/.xlsx file generation) 
- Swing (GUI)
