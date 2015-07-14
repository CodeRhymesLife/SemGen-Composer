## Success!

The SemGen-Composer has been integrated with [SemGen](http://sbp.bhi.washington.edu/projects/semgen)! I was able to integrate it over a period of a few months during the first year of my PhD program at the University of Washington. Quite a bit was upgraded during the integration, such as the name (the composer is now referred to as the "stage"); however, many of the core concepts remain. Check out on the executable version on the [SemGen website](http://sbp.bhi.washington.edu/projects/semgen) and/or the code on [github](https://github.com/thompsct/SemGen/tree/master/cfg/stage).

# SemGen-Composer

SemGen-Composer is an open source Java application built for [SemGen](http://sbp.bhi.washington.edu/projects/semgen) - an experimental software tool for automating the modular composition and decomposition of biosimulation models. The purpose of SemGen-Composer is to leverage the powerful functions of SemGen through an intuitive drag and drop user interface. I developed this application while volunteering for Dr. Maxwell Neal, Post-doctoral Research Fellow at the University of Washington in the Biomedical and Health Informatics (BHI) department.

<img src="https://github.com/rcjames1004/SemGen-Composer/raw/master/readme%20resources/model%20flyout.png">

## Get Started
<ol>
  <li>Install Eclipse (http://www.eclipse.org/downloads/packages/eclipse-standard-431/keplersr1)</li>
  <li>Clone this repository</li>
  <li>Add cloned repository directory to a workspace in eclipse</li>
  <li>Run!</li>
</ol>

## Features
<ul>
  <li>Add models from a local or network directory</li>
  <img src="https://github.com/rcjames1004/SemGen-Composer/raw/master/readme%20resources/models.png" />
  <br>

  <li>Merge models</li>
  <img src="https://github.com/rcjames1004/SemGen-Composer/raw/master/readme%20resources/merged%20models.png" />
  <br>
  
  <li>Edit model properties</li>
  <img src="https://github.com/rcjames1004/SemGen-Composer/raw/master/readme%20resources/property%20mappings%20panel.png" />
</ul>

## High Level Code Structure

### Model
<ul>
  <li>[SemGen](https://github.com/rcjames1004/SemGen-Composer/blob/master/src/semGen/SemGen.java) - A singleton class representing the Sem Gen engine. Contains all the models and exposes public APIs for Annotating, Encoding, Extracting, and Merging (only Merge iimplemented)</li>
  <li>[Models](https://github.com/rcjames1004/SemGen-Composer/tree/master/src/semGen/models) - This folder contains the classes for Models, their properties, and listeners for model and model property changes</li>
  <li>Most of this code has corresponding unit tests to validate that it works properly.</li>
</ul>

### View
<ul>
  <li>
    The view layer listens for events from the model layer and creates appropriate elements
    <ul>
      <li>In this way the view is separate from the model</li>
      <li>Allows elements in the view layer to be swapped out without effecting the model</li>
    </ul>
  </li>
  <li>
    All view components are contained in “ui” folders
    <ul>
      <li>For example, here are all of the [model ui components](https://github.com/rcjames1004/SemGen-Composer/tree/master/src/semGen/models/ui)</li>
    </ul>
  </li>
  <li>I also added some [standalone ui components](https://github.com/rcjames1004/SemGen-Composer/tree/master/src/ui) that can be reused in other java projects</li>
  <li>I didn't have time to write unit tests for the view layer. Hopefully coming soon :)</li>
</ul>


## Future Plans
<ul>
  <li>Fix bugs (https://github.com/rcjames1004/SemGen-Composer/issues)</li>
  <li>Incorporate with SemGen</li>
</ul>
