const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const tc = Number(input[0]);
let answer = [];
const arr1 = new Uint32Array(1500001);
const arr2 = new Uint32Array(1500001);

for (let t = 0; t < tc; t++) {
  let a = input[1 + 2 * t];
  let b = input[1 + 2 * t + 1];

  getArray(a, arr1);
  getArray(b, arr2);

  answer.push(compare(arr1, arr2));
}
console.log(answer.join("\n"));

function getArray(str, counter) {
  counter.fill(0);

  const open = "(";
  const close = ")";
  let depth = 0; // ret index
  let maxDepth = 0;
  for (let i = 0; i < str.length; i++) {
    s = str[i];
    if (s === open) {
      depth++;
      if (depth > maxDepth) maxDepth = depth;
    } else if (s === close) {
      depth--;
      if (str[i - 1] === open) {
        counter[depth]++;
      }
    } else {
      console.log("?");
    }
  }

  // 각 자릿수에 2이상은 다음 자릿수로 넘겨주기
  for (let i = 0; i < maxDepth + 1; i++) {
    if (counter[i] > 1) {
      counter[i + 1] += Math.floor(counter[i] / 2);
      counter[i] = counter[i] % 2;
    }
  }
}

function compare(arr1, arr2) {
  for (let i = 1500000; i > -1; i--) {
    if (arr1[i] < arr2[i]) return "<";
    if (arr1[i] > arr2[i]) return ">";
  }
  return "=";
}
