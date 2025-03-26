### 풀이

알고리즘

1. node 클래스를 만들어서 자식을 관리함
2. root 클래스는 처음 노드를 가리킴
3. 각 자식을 관리하기 위해서는 맵으로 관리하면 좋을듯? (key : 자식 이름, value : 자식 인스턴스)
4. 빡센게 출력할 때 사전 순서로 해야함.(출력 할 때 dfs?) ⇒ 사전순 정렬하면서 dfs

코드 ⇒ 틀림

```jsx
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
    iter.sort((a, b) => a.charCodeAt() - b.charCodeAt());
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

```

코드 ⇒ sort 자체로 문자열 정렬이됨

```jsx
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

```