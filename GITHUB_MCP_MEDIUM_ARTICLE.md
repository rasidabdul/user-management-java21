# Automating GitHub Workflows with IBM Bob and the GitHub MCP Server

> A practical walkthrough of installing the official GitHub MCP Server in IBM Bob using a pre-built binary, then using a single natural-language prompt to commit, push, open a pull request, and merge — all without leaving your editor.

---

## Introduction

IBM Bob is an AI-powered coding assistant embedded in VS Code. One of its most powerful capabilities is **MCP (Model Context Protocol) tool integration** — a plugin system that lets Bob call external APIs on your behalf. The **GitHub MCP Server** is one such plugin: it gives Bob direct access to the GitHub API so you can manage repositories, branches, pull requests, and more through plain English prompts.

This article walks through two real-world activities:

1. **Installing and configuring the GitHub MCP Server** in IBM Bob globally using the official pre-built binary.
2. **Using a single prompt** to stage changes, push a branch, open a PR, and merge it — demonstrated on a live Java 21 microservice project.

---

## Part 1 — Installing and Configuring the GitHub MCP Server

### What Is the GitHub MCP Server?

The [GitHub MCP Server](https://github.com/github/github-mcp-server) is GitHub's official MCP server. It wraps the GitHub REST API and exposes operations like `create_pull_request`, `merge_pull_request`, `list_issues`, `search_repositories`, and more as callable tools that Bob can invoke during a conversation.

### Prerequisites

- IBM Bob extension installed in VS Code.
- A GitHub account with a **Personal Access Token (PAT)** that has `repo`, `read:org`, `read:user`, and `workflow` scopes.
- No Docker or Node.js required — the server ships as a standalone binary.

### Step 1 — Generate a GitHub Personal Access Token (PAT)

1. Go to **[https://github.com/settings/tokens](https://github.com/settings/tokens)**
2. Click **"Generate new token" → "Generate new token (classic)"**
3. Give it a descriptive name (e.g., `bob-mcp-server`)
4. Select the following scopes:
   - `repo` — full repository access
   - `read:org` — read org membership
   - `read:user` — read user profile
   - `workflow` — manage GitHub Actions
5. Click **"Generate token"** and copy it immediately (it won't be shown again)

> **Security note:** Never commit your PAT to source control. Treat it like a password: use minimum required scopes and rotate it regularly.

### Step 2 — Download the Pre-Built Binary

The GitHub MCP Server ships as a standalone binary for macOS, Linux, and Windows. No Docker or Node.js runtime is needed.

Check your CPU architecture:

```bash
uname -m
# arm64  → Apple Silicon Mac
# x86_64 → Intel Mac
```

Download and extract the correct binary for your machine:

**Apple Silicon (arm64):**

```bash
mkdir -p "/Users/<you>/Documents/IBM Bob/MCP/github-mcp-server"

curl -L "https://github.com/github/github-mcp-server/releases/download/v0.31.0/github-mcp-server_Darwin_arm64.tar.gz" \
  -o "/Users/<you>/Documents/IBM Bob/MCP/github-mcp-server/github-mcp-server_Darwin_arm64.tar.gz"

tar -xzf "/Users/<you>/Documents/IBM Bob/MCP/github-mcp-server/github-mcp-server_Darwin_arm64.tar.gz" \
  -C "/Users/<you>/Documents/IBM Bob/MCP/github-mcp-server/"

chmod +x "/Users/<you>/Documents/IBM Bob/MCP/github-mcp-server/github-mcp-server"
```

**Intel Mac (x86_64):** Replace `arm64` with `x86_64` in the URL above.

Check the [releases page](https://github.com/github/github-mcp-server/releases/latest) for the latest version.

### Step 3 — Configure Bob's MCP Settings

Open (or create) the Bob global MCP settings file at:

```
~/.bob/settings/mcp_settings.json
```

Add the following configuration:

```json
{
  "mcpServers": {
    "github": {
      "command": "/Users/<you>/Documents/IBM Bob/MCP/github-mcp-server/github-mcp-server",
      "args": ["stdio"],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "<YOUR_PAT_HERE>"
      },
      "disabled": false,
      "alwaysAllow": [],
      "disabledTools": []
    }
  }
}
```

Replace `<you>` with your macOS username and `<YOUR_PAT_HERE>` with the PAT you generated in Step 1.

**Key configuration notes:**
- `"command"` — absolute path to the downloaded binary
- `"args": ["stdio"]` — runs the server in stdio mode (required for Bob's MCP integration)
- `"disabled": false` — ensures the server starts automatically with Bob
- `"alwaysAllow": []` — no tools are auto-approved without confirmation (recommended)

### Step 4 — Verify the Connection

After saving the file, Bob automatically starts the MCP server. Verify it is connected and authenticated by asking Bob:

```
Who am I authenticated as on GitHub?
```

Bob will call the `get_me` tool and return your GitHub profile. A successful response looks like:

```json
{
  "login": "rasidabdul",
  "id": 229663025,
  "profile_url": "https://github.com/rasidabdul",
  "details": {
    "public_repos": 9,
    "followers": 0,
    "following": 0
  }
}
```

If Bob returns your GitHub username, the server is live and working.

### What the Server Enables

Once configured, Bob can perform the following GitHub operations through natural language:

| Capability | Example Prompt |
|---|---|
| View your profile | "Who am I authenticated as on GitHub?" |
| List repositories | "Show me all my public repositories" |
| Search code | "Find all Java files in my repos that use virtual threads" |
| List issues | "Show me all open issues in this repo" |
| Create a branch | "Create a branch called feature/my-feature" |
| Open a pull request | "Create a PR from this branch to main" |
| Merge a pull request | "Merge PR #2 using squash" |
| Fetch PR details | "Summarize the changes in PR #5" |
| Push files | "Push these changes to the remote repository" |
| Inspect commits | "Show me the last 5 commits on main" |

---

## Part 2 — The Entire GitHub Workflow from One Prompt

### The Project

The demonstration project is **user-management-java21** — a Spring Boot 3 / Java 21 microservice with MongoDB persistence, virtual threads, Spock unit tests, and Karate API tests. The repository is hosted at `https://github.com/rasidabdul/user-management-java21`.

The project had several uncommitted local changes: modified source files, new documentation, a Draw.io architecture diagram, and build-fix notes — the kind of accumulated work that normally requires multiple terminal commands and GitHub UI interactions to ship.

### The Prompt — One Sentence, Five Actions

Here is the **complete, unedited prompt** typed into the Bob chat panel:

> *"Add all the changes and push to remote as new branch, create a pr and merge the pr. Here is the remote repository: https://github.com/rasidabdul/user-management-java21"*

That single sentence triggered Bob to autonomously:

1. ✅ Check the current git status and identify all uncommitted changes
2. ✅ Stage and commit everything to the existing feature branch
3. ✅ Push the branch to the remote repository
4. ✅ Generate a meaningful PR description and open a pull request
5. ✅ Merge the pull request into `main`

No shell scripts. No manual GitHub UI clicks. No separate `git add`, `git commit`, `git push`, or `gh pr create` commands.

### The Result — Bob's Completion Output

When Bob finished, it reported:

```
🎉 Task Completed
All steps completed successfully:

• Committed all local changes (modified files + untracked docs/diagrams)
  to branch feature/user-management-improvements

• Pushed the branch to rasidabdul/user-management-java21

• Created PR #2 — "feat: add Draw.io mode, process flow diagram,
  and code improvements"

• Merged PR #2 into main via squash merge (commit 7b7ee91)
```

The entire workflow — from detecting uncommitted changes to a merged PR on `main` — completed in under two minutes.

### What Bob Did Under the Hood

For readers who want to understand the mechanics, here is what Bob orchestrated behind the scenes in response to that single prompt.

#### 1 — Assessed the Repository State

Bob ran `git status` and `git log --oneline -5` internally to understand the current state before taking any action.

**What it found:**
- Active branch: `feature/user-management-improvements`
- Modified tracked files: `README.md`, `UserRequest.java`, `UserService.java`
- Untracked new files: `AGENTS.md`, `BUILD_FIX_RESOLUTION.md`, `BUILD_FIX_SUMMARY.md`, `drawio-mode.yaml`, `user-management-process-flow.drawio`, and others

#### 2 — Staged and Committed All Changes

Bob staged everything and committed with a descriptive message:

```bash
git add -A
git commit -m "feat: add agents docs, build fixes, drawio diagram, and code improvements"
```

**Commit result:** 12 files changed, 2,367 insertions, 289 deletions.

#### 3 — Pushed the Branch to Remote

```bash
git push origin feature/user-management-improvements
```

```
To https://github.com/rasidabdul/user-management-java21.git
   c409f84..1013e43  feature/user-management-improvements -> feature/user-management-improvements
```

#### 4 — Generated a Meaningful PR Description

Bob called its built-in `generate_description_from_diff` capability, comparing the feature branch against `main`. Rather than a generic "merged changes" message, it produced a **per-file narrative** explaining the purpose of each change — immediately useful for reviewers and future maintainers.

#### 5 — Opened the Pull Request via GitHub MCP Server

Bob called the GitHub MCP Server's `create_pull_request` tool with the generated description:

```
PR Title : feat: add Draw.io mode, process flow diagram, and code improvements
Base     : main
Head     : feature/user-management-improvements
PR URL   : https://github.com/rasidabdul/user-management-java21/pull/2
```

#### 6 — Merged the Pull Request

Bob called `merge_pull_request` with the squash strategy:

```
Merge commit : 7b7ee91485411cb970479b10c30fcc04708e856f
Status       : Pull Request successfully merged
```

### Why This Matters

Traditional GitHub workflows require context-switching between your editor, a terminal, and the GitHub web UI. With the GitHub MCP Server configured in Bob, the entire flow collapses into a single conversational instruction. Bob handles the sequencing, error checking, and API calls — you stay focused on the work, not the tooling.

---

## Key Takeaways

### 1. Natural Language Is the Interface
You don't need to remember `git` flags, GitHub CLI syntax, or API endpoints. A plain English description of your intent is enough for Bob to orchestrate the full workflow.

### 2. No Docker or Node.js Required
The GitHub MCP Server ships as a standalone binary. Download it, point Bob's config at it, and you're done. No container runtime, no package manager, no runtime dependencies.

### 3. MCP Tools Extend Bob's Reach
The GitHub MCP Server is one of many available integrations. The same pattern applies to Jira, Slack, databases, and other services — install the server, configure credentials, and Bob gains new capabilities.

### 4. Bob Generates Meaningful PR Descriptions
Rather than a generic "merged changes" message, Bob analyzed the diff and produced a per-file narrative explaining the purpose of each change. This is immediately useful for code reviewers and future maintainers.

### 5. The Workflow Is Auditable
Every `git` command and API call Bob made was visible in the VS Code terminal and chat panel. You can review, approve, or override any step before it executes.

### 6. Security Responsibility Remains with You
The GitHub PAT gives Bob real write access to your repositories. Use minimum required scopes, rotate it regularly, and never commit it to source control.

---

## Full Prompt Reference

### Installing the GitHub MCP Server

> *"Add the GitHub MCP server to Bob globally to integrate with my GitHub account: https://github.com/rasidabdul"*

### Pushing Changes and Creating a PR

> *"Add all the changes and push to remote as a new branch, create a PR and merge the PR. Here is the remote repository: https://github.com/rasidabdul/user-management-java21"*

---

## Resources

- [GitHub MCP Server — Official Repository](https://github.com/github/github-mcp-server)
- [GitHub MCP Server — Releases](https://github.com/github/github-mcp-server/releases/latest)
- [Model Context Protocol (MCP) specification](https://modelcontextprotocol.io)
- [GitHub Personal Access Tokens](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)
- [Demo repository — user-management-java21](https://github.com/rasidabdul/user-management-java21)
- [Merged PR #2](https://github.com/rasidabdul/user-management-java21/pull/2)