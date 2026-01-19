const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const timestamp = {};
for (let i = 0; i < n; i++) {
  let [s, e] = input[1 + i].split(" ").map(Number);

  if (timestamp[s] === undefined) {
    timestamp[s] = 1;
  } else {
    timestamp[s]++;
  }

  if (timestamp[e] === undefined) {
    timestamp[e] = -1;
  } else {
    timestamp[e]--;
  }
}

const timestampAscending = [];
for (let key of Object.keys(timestamp).map(Number)) {
  timestampAscending.push([key, timestamp[key]]);
}
timestampAscending.sort((a, b) => a[0] - b[0]);

let prefixSum = 0;
let maxNumber = -1;
let maxStartTime = -1;
let maxEndTime = -1;
let isMax = false;

for (let [time, flag] of timestampAscending) {
  prefixSum += flag;

  if (prefixSum > maxNumber) {
    maxNumber = prefixSum;
    maxStartTime = time;
    isMax = true;
  }

  if (isMax && prefixSum < maxNumber) {
    maxEndTime = time;
    isMax = false;
  }
}

let answer = `${maxNumber}\n${maxStartTime} ${maxEndTime}`;
console.log(answer);
