#!/bin/sh
storm jar eagle-backgw-stream.jar com.linkstec.raptor.eagle.PresentChartTopology QnfsChartTopology1 QNFS1 1
storm jar eagle-backgw-stream.jar com.linkstec.raptor.eagle.PresentChartTopology QnfsChartTopology2 QNFS1 2
storm jar eagle-backgw-stream.jar com.linkstec.raptor.eagle.PresentChartTopology QnfsChartTopology3 QNFS1 3
storm jar eagle-backgw-stream.jar com.linkstec.raptor.eagle.PresentChartTopology QnfsChartTopology4 QNFS1 4
storm jar eagle-backgw-stream.jar com.linkstec.raptor.eagle.PresentChartTopology QnfsChartTopology5 QNFS1 5
storm jar eagle-backgw-stream.jar com.linkstec.raptor.eagle.PresentOfferTopology QnfsTopology1 QNFS1 1 2
storm jar eagle-backgw-stream.jar com.linkstec.raptor.eagle.PresentOfferTopology QnfsTopology2 QNFS1 3 5
