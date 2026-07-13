import { useState } from "react";
import {
  Box,
  Button,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import API from "../services/api";
import QRCode from "react-qr-code";

export default function UrlForm() {
  const [url, setUrl] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [history, setHistory] = useState([]);

  const shortenUrl = async () => {
    if (!url) return;

    try {
      setLoading(true);

      const response = await API.post("", {
        url: url,
      });

      setResult(response.data);
      setHistory((prev) => [response.data, ...prev]);
    } catch (err) {
      alert("Something went wrong");
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  const copyUrl = () => {
    navigator.clipboard.writeText(result.shortUrl);
    alert("Copied!");
  };

  return (
    <Paper
      elevation={4}
      sx={{
        p: 4,
        maxWidth: 700,
        mx: "auto",
        mt: 6,
        borderRadius: 3,
      }}
    >
      <Typography variant="h4" align="center" gutterBottom>
  Cloud URL Shortener by Aryan k
</Typography>

<Typography
  align="center"
  color="text.secondary"
  sx={{ mb: 3 }}
>
  React • Spring Boot • MySQL • AWS
</Typography>

      <TextField
        fullWidth
        label="Enter Long URL"
        value={url}
        onChange={(e) => setUrl(e.target.value)}
      />

      <Button
        variant="contained"
        fullWidth
        sx={{ mt: 3 }}
        onClick={shortenUrl}
        disabled={loading}
      >
        {loading ? "Generating..." : "Shorten URL"}
      </Button>
        {result && (
  <Box mt={4}>

    <Typography>
      <b>Original URL:</b>
    </Typography>

    <Typography
      sx={{
        wordBreak: "break-all",
        overflowWrap: "break-word",
      }}
    >
      {result.originalUrl}
    </Typography>

    <Typography mt={2}>
      <b>Short URL:</b>
    </Typography>

    <Typography>
      <a
        href={result.shortUrl}
        target="_blank"
        rel="noopener noreferrer"
        style={{ color: "#1976d2" }}
      >
        {result.shortUrl}
      </a>
    </Typography>

    <Button
      sx={{ mt: 2 }}
      variant="outlined"
      onClick={copyUrl}
    >
      Copy URL
    </Button>

    <Box mt={4} display="flex" justifyContent="center">
      <QRCode value={result.shortUrl} size={140} />
    </Box>

    {history.length > 0 && (
      <Box mt={5}>
        <Typography variant="h6" gutterBottom>
          Recent URLs
        </Typography>

        <table
          style={{
            width: "100%",
            borderCollapse: "collapse",
          }}
        >
          <thead>
            <tr style={{ background: "#1976d2", color: "white" }}>
              <th style={{ padding: "10px" }}>Short URL</th>
              <th style={{ padding: "10px" }}>Clicks</th>
            </tr>
          </thead>

          <tbody>
            {history.map((item, index) => (
              <tr key={index}>
                <td
                  style={{
                    padding: "10px",
                    border: "1px solid #ddd",
                  }}
                >
                  <a
                    href={item.shortUrl}
                    target="_blank"
                    rel="noreferrer"
                  >
                    {item.shortUrl}
                  </a>
                </td>

                <td
                  style={{
                    textAlign: "center",
                    border: "1px solid #ddd",
                  }}
                >
                  {item.clickCount}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </Box>
    )}

  </Box>
)}

      
    </Paper>
  );
}