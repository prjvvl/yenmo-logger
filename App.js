import './App.css';

import * as React from 'react';
// import useWebSocket from 'react-use-websocket';

const server_url = 'ws://127.0.0.1:8000/';



function App() {

  const [data, setData] = React.useState("No Logs");

  const connection = React.useRef(null);

  React.useEffect(() => {
    if (!(connection && connection.current && connection.current.readyState === 1)) {
      connection.current = new WebSocket(server_url);
      connection.current.onopen = () => console.log("Websocket is open ");
      connection.current.onclose = () => console.log("Websocket is close");
      connection.current.onmessage = (e) => {
        console.log(e);
        console.log(e.data);
        setData(e.data);
      }
    }
  }, []);

  // React.useEffect(() => {
  //   const websocket = new WebSocket(server_url);
  //   websocket.onopen = () => console.log('Connected!');
  //   websocket.onmessage = (e) => {
  //     console.log(e.data);
  //     setData(e.data);
  //   }
  //   return () => websocket.close();
  // }, []);
  

  return (
    <div>
      <div>Hello World</div>
      <div>{data}</div>
    </div>
    
  );
}

export default App;
