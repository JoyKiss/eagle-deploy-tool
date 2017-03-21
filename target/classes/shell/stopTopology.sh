#!/bin/sh
storm kill QnfsChartTopology1 -w 1
storm kill QnfsChartTopology2 -w 1
storm kill QnfsChartTopology3 -w 1
storm kill QnfsChartTopology4 -w 1
storm kill QnfsChartTopology5 -w 1
storm kill QnfsTopology1 -w 1
storm kill QnfsTopology2 -w 1

