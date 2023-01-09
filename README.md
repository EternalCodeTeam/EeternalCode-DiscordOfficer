# EternalCode.pl - Discord Officer

### Made with ❤ by EternalCode.pl

### Pre-requisites:

Node.js with LTS version
Yarn (or else)

### Installation:

```bash
yarn install
yarn start
```

## Or u can use docker image
U need first create directory and file .env (copy .env.example from repo) and run:
```bash
docker run -d eternalcode/discordofficer:latest -v $(pwd)/eternalcode-discordofficer/.env:/home/eternalcode/.env
```