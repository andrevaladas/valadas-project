@echo off
rem ======================================================================
rem Copyright 2002-2004 Laszlo Systems, Inc.  All Rights Reserved.
rem Unauthorized use, duplication or distribution is strictly prohibited.
rem This software is the proprietary information of Laszlo Systems, Inc.
rem Use is subject to license terms.
rem ======================================================================

C:
cd C:\Program Files\OpenLaszlo Server 4.0.3\Server\lps-4.0.3\lps\utils
call startTomcat.bat
start startup-static.html
