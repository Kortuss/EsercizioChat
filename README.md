Consegna (pag159):
Progetta e realizza un'applicazione client-server che realizza una chat TPSIT_chat, dove ogni utente interagisce nella 
chat attraverso un client:
• il server riceve le richieste di connessione da parte dei client e funziona come punto di raccolta dei messaggi;✅
• ogni client, al momento della richiesta di connessione, chiede all'utente e fornisce al server un nickname con cui l'utente 
comparirà in chat; (qui ho fatto che l'utente può scegliere dal menù a tendina di selezionarlo, altrimenti gli è assegnato un id di default✅)
• il server invierà agli altri client connessi i messaggi ricevuti da un client specifico, anteponendo a essi il nickname che 
l'utente ha scelto al momento della connessione✅;
• il server, per ogni client, mantiene una variabile inizializzata a zero, per contare le interazioni avvenute con il client nel 
corso dell'esecuzione. alla ricezione di ogni nuovo messaggio, il server incrementa la variabile di un'unità;
• quando il client si disconnette, il server stampa a video un messaggio o nale che riporta il numero di interazioni com-
plessivamente avvenute con il client.(Io il server ho pensato di renderlo un app da terminale, stamperò quindi da terminale il numero di interazione di un client)

