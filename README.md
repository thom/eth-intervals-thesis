# An Advanced Scheduler for Intervals - Master’s Thesis

Intervals are a new, higher-level primitive for parallel programming
allowing programmers to directly construct the program schedule. They
were developed at ETH Zürich and are the subject of my master's
thesis.

The intervals implementation in Java uses a work-stealing scheduler
where a worker running out of work tries to steal work from
others. The scope of this thesis is to improve the performance of the
intervals scheduler.

This repository contains the master's thesis documentation.

## Creating documents

The thesis documentation is in the folder ```output```.

Execute ```make pdf``` to re-create a PDF version of the thesis. ```make
clean``` removes temporary files. Use ```make help``` and ```make
man``` for help.

## Required software

* GNU make
* LaTeX, pdfTeX and Biber (BibTeX alternative)
* latexmk: Version 4.64a is provided in the ```bin/``` directory.
* ps2pdf, epstopdf, pdftops and pdfnup
* PDF viewer (e.g. evince)
* PS viewer (e.g. gv)
* DVI viewer (e.g. xdvi)
* LaTeX checker (e.g. lacheck)
* Perl and perl-doc

## License

- **[GPLv3](http://www.gnu.org/licenses/gpl-3.0.txt)**
- Copyright 2010 © <a href="https://github.com/thom" target="_blank">Thomas Weibel</a>.