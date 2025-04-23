const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

class Node {
  constructor(value, depth) {
    this.value = value;
    this.childs = {};
    this.depth = depth;
  }

  add(arr) {
    if (arr.length === 0) { return; }
    let ch = arr.shift();
    if (this.childs[ch] === undefined) { this.childs[ch] = new Node(ch, this.depth + 1); }
    this.childs[ch].add(arr);
  }

  search() {
    let iter = Object.keys(this.childs);
    let filler = "--".repeat(this.depth);
    iter.sort();
    for (let ch of iter) {
      console.log(`${filler}${ch}`);
      this.childs[ch].search();
    }
  }
}
const n = Number(input[0]);
let root = new Node(-1, 0);

for (let i = 0; i < n; i++) {
  let [k, ...args] = input[1 + i].trim().split(" ");
  root.add(args);
}
root.search();
