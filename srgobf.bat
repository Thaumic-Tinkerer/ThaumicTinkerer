java -cp BON.jar immibis.bon.cui.MCPRemap -mcp D:/MCPDev/MCP16/forge/mcp -in TC.zip -out TCDEOBF_IM.zip -side UNIVERSAL -from SRG -to OBF -ref D:/MCPDev/MCP16/forge/mcp/jars/versions/1.6.4/1.6.4.jar
java -cp BON.jar immibis.bon.cui.MCPRemap -mcp D:/MCPDev/MCP16/forge/mcp -in TCDEOBF_IM.zip -out TCDEOBF.zip -side UNIVERSAL -from OBF -to MCP -ref D:/MCPDev/MCP16/forge/mcp/jars/versions/1.6.4/1.6.4.jar
del TCDEOBF_IM.zip
pause